package com.example.magesticreader.interactors

import com.example.magesticreader.data.DocumentRepository

class GetDocuments(private val repository: DocumentRepository) {
    suspend operator fun invoke() = repository.getDocuments()
}