package com.viettran.reading_story_web.controller;

import com.viettran.reading_story_web.dto.request.RatingRequest;
import com.viettran.reading_story_web.dto.response.*;
import com.viettran.reading_story_web.dto.request.StoryRequest;
import com.viettran.reading_story_web.enums.Gender;
import com.viettran.reading_story_web.enums.StoryStatus;
import com.viettran.reading_story_web.service.StoryService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/stories")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StoryController {
    StoryService storyService;

    @PostMapping
    ApiResponse<StoryResponse> createStory(@Valid @ModelAttribute StoryRequest request) throws IOException {
        return ApiResponse.<StoryResponse>builder()
                .result(storyService.createStory(request))
                .build();
    }

    @GetMapping
    ApiResponse<PageResponse<StoryResponse>> getStories(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "32") int size
    ) {
        return ApiResponse.<PageResponse<StoryResponse>>builder()
                .result(storyService.getStories(page, size))
                .build();
    }

    @GetMapping("/{storyId}")
    ApiResponse<StoryResponse> getStoryById(@PathVariable Integer storyId){
        return ApiResponse.<StoryResponse>builder()
                .result(storyService.getStoryById(storyId))
                .build();
    }

    @DeleteMapping("/{storyId}")
    ApiResponse<String> deleteStory(@PathVariable Integer storyId){
        storyService.deleteStory(storyId);
        return ApiResponse.<String>builder()
                .result("Story has been deleted")
                .build();
    }

    @PutMapping("{storyId}")
    ApiResponse<StoryResponse> updateStory(@ModelAttribute StoryRequest request,@PathVariable Integer storyId){
        return ApiResponse.<StoryResponse>builder()
                .result(storyService.updateStory(request, storyId))
                .build();
    }

    @GetMapping("/search")
    ApiResponse<PageResponse<StoryResponse>> searchStories(
            @RequestParam String keyword,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size
    ) {
        return ApiResponse.<PageResponse<StoryResponse>>builder()
                .result(storyService.searchStories(keyword, page, size))
                .build();
    }

    @PatchMapping("/{storyId}/rate")
    ApiResponse<StoryResponse> rateStory(
            @PathVariable Integer storyId,
            @RequestBody RatingRequest request)
    {

        return ApiResponse.<StoryResponse>builder()
                .result(storyService.rateStory(storyId , request))
                .build();
    }

    @GetMapping("/by-genre")
    ApiResponse<PageResponse<StoryResponse>> getStoriesByGenre(
            @RequestParam Integer queryCode,
            @RequestParam(required = false) StoryStatus status,
            @RequestParam(required = false, defaultValue = "1") int sort,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "32") int size
    ) {
        return ApiResponse.<PageResponse<StoryResponse>>builder()
                .result(storyService.getStoriesByGenreQueryCode(queryCode, status, sort, page, size))
                .build();
    }

    @GetMapping("/top-views")
    ApiResponse<List<StoryResponse>> getTop10StoriesByViewCount(){
        return ApiResponse.<List<StoryResponse>>builder()
                .result(storyService.getTop10StoriesByViewCount())
                .build();
    }
    // bảng follow
    @GetMapping("/my-followed-story")
    ApiResponse<List<FollowResponse>> getFollowedStories(){
        return ApiResponse.<List<FollowResponse>>builder()
                .result(storyService.getFollowedStories())
                .build();
    }

    // query: ?gender=GENDER.MALE
    @GetMapping("/gender")
    ApiResponse<PageResponse<StoryResponse>> getStoriesByGender(
            @RequestParam Gender gender,
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "32") int size
    ){
        return ApiResponse.<PageResponse<StoryResponse>>builder()
                .result(storyService.getStoriesByGender(page, size, gender))
                .build();
    }

    @GetMapping("/hot")
    ApiResponse<PageResponse<StoryResponse>> getHotStories(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "32") int size
    ){
        return ApiResponse.<PageResponse<StoryResponse>>builder()
                .result(storyService.getHotStories(page, size))
                .build();
    }

    @GetMapping("/my-followed-story/{storyId}")
    ApiResponse<FollowResponse> getFollowedStoryByUserIdAndStoryId(
            @PathVariable Integer storyId
    ){
        return ApiResponse.<FollowResponse>builder()
                .result(storyService.getFollowedStoryByUserIdAndStoryId(storyId))
                .build();
    }
}