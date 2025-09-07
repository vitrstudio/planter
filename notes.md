### TODO

- the github authentication is working
- now I think I need to add the application auth filter
- I need to check if everything is correctly stored in the DB


### Current project status

Currently the github signin works, although it could be improved. Right now:
- Endpoint /auth/github/login returns the github signin url
- Endpoint /auth/github/callback transforms the code & state provided by the github authentication process into a token
- Endpoint /auth/github/success validates the token and returns the user

What I think should happen instead:
- (optional) Logic for /auth/github/login may be handled in the frontend
- Create a /oauth/github/signin endpoint that replaces the callback endpoint
  - Receives the code and state from the frontend
  - Calls a github client to validate the token
  - If successful, upserts the user into the database
    - Stores the github token
    - Stores the github user details
  - If successful, generates a JWT token containing user details and github token
  - Use access and refresh tokens to access the platform
  - Once either the refresh token or the github token expire
    - The user is logged out
    - The user must sign in again using the /oauth/github/signin endpoint
- The endpoint /success can be abolished
- Instead we should have just /signin and /refresh endpoints