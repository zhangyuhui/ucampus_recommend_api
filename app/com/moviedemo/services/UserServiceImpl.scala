package com.moviedemo.services

import com.google.inject.Inject
import com.moviedemo.domain.interfaces._

class UserServiceImpl @Inject()(implicit val repository: UserRepository) extends UserService {
}
