package studio.vitr.planter.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy.STATELESS
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfigurationSource
import studio.vitr.planter.auth.AuthService
import studio.vitr.planter.auth.JwtAuthenticationFilter
import studio.vitr.planter.service.GithubUserService
import studio.vitr.planter.service.UserService

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val authService: AuthService,
    private val userService: UserService,
    private val githubUserService: GithubUserService,
    private val corsConfigurationSource: CorsConfigurationSource,
) {

    @Bean
    @Order(1)
    fun publicSecurityChain(http: HttpSecurity): SecurityFilterChain = http
        .securityMatchers { it
            .requestMatchers("/health")
            .requestMatchers("/auth/**")
            .requestMatchers("/error")
            .requestMatchers("/swagger-ui/**")
            .requestMatchers("/api-docs/**")
            .requestMatchers("/swagger-ui.html")
        }
        .cors { it.configurationSource(corsConfigurationSource) }
        .csrf { it.disable() }
        .sessionManagement { it.sessionCreationPolicy(STATELESS) }
        .authorizeHttpRequests { it.anyRequest().permitAll() }
        .build()

    @Bean
    @Order(2)
    fun protectedSecurityChain(http: HttpSecurity): SecurityFilterChain = http
        .cors { it.configurationSource(corsConfigurationSource) }
        .csrf { it.disable() }
        .sessionManagement { it.sessionCreationPolicy(STATELESS) }
        .authorizeHttpRequests { it.anyRequest().authenticated() }
        .addFilterBefore(jwtAuthFilter(), UsernamePasswordAuthenticationFilter::class.java)
        .build()

    private fun jwtAuthFilter() = JwtAuthenticationFilter(authService, userService, githubUserService)
}
