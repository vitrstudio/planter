package studio.vitr.planter.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import studio.vitr.planter.model.User

@Repository
interface UserRepository : JpaRepository<User, Long>