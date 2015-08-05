package com.moviedemo.services

import com.google.inject.Inject
import com.moviedemo.domain.interfaces._

class MovieServiceImpl @Inject()(implicit val repository: MovieRepository) extends MovieService {
}
