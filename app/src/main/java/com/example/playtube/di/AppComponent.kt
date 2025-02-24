package com.example.playtube.di

import android.app.Application
import com.example.playtube.features.videolist.VideoListFragment
import dagger.Component
import dagger.Module

@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(fragment: VideoListFragment)
}

@Module(includes = [NetworkModule::class])
class AppModule