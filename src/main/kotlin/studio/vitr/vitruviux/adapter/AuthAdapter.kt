package studio.vitr.vitruviux.adapter

import org.springframework.stereotype.Component
import studio.vitr.vitruviux.constants.Properties.USER_ID
import studio.vitr.vitruviux.errors.MissingExpectedParameter
import studio.vitr.vitruviux.model.dto.Session
import studio.vitr.vitruviux.model.db.User
import studio.vitr.vitruviux.model.api.SessionResponse

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