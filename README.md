# User Vehicle project

## Project setup
1/ Run `mvn clean install` to generate annotated mapstruct mapper(s).
<br>
2/ Import project into IDE.
<br>
3/ Add build path for `target\generated-sources\annotations`

## Test data setup
1/ Open H2 console via browser.
<br>
2/ Execute SQL `INSERT INTO USER (USER_CODE , FIRST_NAME , LAST_NAME , EMAIL , PASSWORD , ROLE ) VALUES('3ee77a2e-1057-4a64-9335-20092e872ab2', 'admin', 'admin', 'admin@gmail.com', 'a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3', 'ROLE_ADMIN')`

## API(s) list
- All api(s) are secured with basic authentication by email & password.
- Only role ROLE_ADMIN has authority to access all api(s).
- User can get user's data itself with ROLE_USER.
### User API(s)
- /user/register
