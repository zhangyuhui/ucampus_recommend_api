package com.moviedemo.domain.interfaces

import com.moviedemo.data.models.{User}
import com.moviedemo.mvc.{RepositoryBase, ServiceBase}

trait UserRepository extends RepositoryBase.T1[User] {
}

trait UserService extends ServiceBase.T1[User] {
}
