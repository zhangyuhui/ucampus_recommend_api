package com.moviedemo.domain.interfaces

import com.moviedemo.data.models.{Movie}
import com.moviedemo.mvc.{RepositoryBase, ServiceBase}

trait MovieRepository extends RepositoryBase.T1[Movie] {
}

trait MovieService extends ServiceBase.T1[Movie] {
}
