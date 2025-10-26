package com.playframework.cric.controllers;

import com.playframework.cric.models.Tag;
import com.playframework.cric.services.TagsService;
import play.mvc.Controller;
import com.google.inject.Inject;
import play.mvc.Result;
import play.libs.Json;
import java.util.List;

import com.playframework.cric.responses.Response;
import com.playframework.cric.responses.PaginatedResponse;

public class TagsController extends Controller {
    private final TagsService tagsService;

    @Inject
    public TagsController (TagsService tagsService) {
        this.tagsService = tagsService;
    }

    public Result getAll(int page, int limit) {
        List<Tag> tags = tagsService.getAll(page, limit);
        int totalCount = 0;
        if(page == 1) {
            totalCount = tagsService.getTotalCount();
        }
        PaginatedResponse<Tag> paginatedResponse = new PaginatedResponse<>(totalCount, tags, page, limit);
        return ok(Json.toJson(new Response(paginatedResponse)));
    }
}