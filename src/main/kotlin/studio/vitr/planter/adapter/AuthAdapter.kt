package studio.vitr.planter.adapter

import org.springframework.stereotype.Component
import studio.vitr.planter.constants.Properties.USER_ID
import studio.vitr.planter.errors.MissingExpectedParameter
import studio.vitr.planter.model.dto.Session
import studio.vitr.planter.model.db.User
import studio.vitr.planter.model.api.SessionResponse

@Component
class AuthAdapter {

    fun toSession(user: User, accessToken: String, refreshToken: String) = Session(
        userId = user.id?.toString() ?: throw MissingExpectedParameter(USER_ID),
        accessToken = accessToken,
        refreshToken = refreshToken
    )

    fun toSessionResponse(session: Session) = SessionResponse(
        userId = session.userId,
        accessToken = session.accessToken,
        refreshToken = session.refreshToken,
    )
}