package com.example.compose_archtiecture_pattern.di

import android.app.Application
import androidx.room.Room
import com.example.compose_archtiecture_pattern.data.data_source.NoteDatabase
import com.example.compose_archtiecture_pattern.data.repository.NoteRepositoryImpl
import com.example.compose_archtiecture_pattern.domain.repository.NoteRepository
import com.example.compose_archtiecture_pattern.domain.use_case.DeleteNotesUseCase
import com.example.compose_archtiecture_pattern.domain.use_case.GetNotesUseCase
import com.example.compose_archtiecture_pattern.domain.use_case.NoteUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application) : NoteDatabase {
        return Room.databaseBuilder(
            app,
            NoteDatabase::class.java,
            NoteDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideRepository(db: NoteDatabase) : NoteRepository {
        return NoteRepositoryImpl(db.noteDao)
    }

    @Provides
    @Singleton
    fun provideUseCases(repository: NoteRepository) : NoteUseCases {
        return NoteUseCases(
            getNotes = GetNotesUseCase(repository),
            deleteNotesUseCase = DeleteNotesUseCase(repository)
        )
    }
}