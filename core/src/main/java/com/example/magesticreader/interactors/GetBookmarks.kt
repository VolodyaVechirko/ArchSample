package com.example.magesticreader.interactors

import com.example.magesticreader.data.BookmarkRepository
import com.example.magesticreader.domain.Document

class GetBookmarks(val repository: BookmarkRepository) {
    suspend operator fun invoke(document: Document) =
        repository.getBookmarks(document)
}