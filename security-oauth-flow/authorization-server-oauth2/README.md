# Spring Boot - Authorization Server

## Test Data

Login: `admin` or `user1`
Password: `password`

## Flow

### Request Authorization Code

We can request with a GET or POST.

```
GET http://localhost:9090/oauth2/auth?grant_type=code&scope=message.read%20message.write&state=my_state_to_assert_redirect&redirect_uri=http%3A%2F%2Flocalhost%3A9099
```


```
POST http://localhost:9090/oauth2/auth
Content-type: application/x-www-form-urlencoded

grant_type=code&scope=message.read%20message.write&state=my_state_to_assert_redirect&redirect_uri=http%3A%2F%2Flocalhost%3A9099
```

To capture the redirect with the authorization code you can run on terminal:

```bash
nc -l 9099
```

## Links

* [OAuth 2 Migration Guide](https://github.com/spring-projects/spring-security/wiki/OAuth-2.0-Migration-Guide)