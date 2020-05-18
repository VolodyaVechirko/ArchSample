package com.example.magesticreader.interactors

import com.example.magesticreader.data.DocumentRepository

class GetOpenDocument(private val repository: DocumentRepository) {
    operator fun invoke() = repository.getOpenDocument()
}