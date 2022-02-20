# Authorization Server

## Grant Type using Client Credentials

Request:

```
POST http://localhost:9000/oauth2/token
Content-Type=application/x-www-form-urlencoded
Authorization: #{base64(<client_id>:<secret>)}

grant_type=client_credentials&scope=message.read message.write
```

Response:
```json
{
    "access_token": "<JWT>",
    "scope": "message.read message.write",
    "token_type": "Bearer",
    "expires_in": "3599"
}
```

