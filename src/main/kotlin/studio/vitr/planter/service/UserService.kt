package studio.vitr.planter.service

import studio.vitr.planter.model.User
import studio.vitr.planter.model.integrations.GithubUser
import java.util.*

interface UserService {
    fun get(id: UUID): User?
    fun upsertUser(githubUser: GithubUser): User
    fun delete(id: UUID)
}