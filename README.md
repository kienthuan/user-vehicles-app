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
2/ Execute SQL
```SQL
INSERT INTO USER (USER_CODE , FIRST_NAME , LAST_NAME , EMAIL , PASSWORD , ROLE ) VALUES('3ee77a2e-1057-4a64-9335-20092e872ab2', 'admin', 'admin', 'admin@gmail.com', 'a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3', 'ROLE_ADMIN')
```
## API(s) list
- All api(s) are secured with basic authentication by email & password.
- Only role **ROLE_ADMIN** has authority to access all api(s).
- User can get user's data itself with **ROLE_USER**.
### User API(s)
- POST `/user/register`: Register user

**Request**: all mandatory
```JSON
{
	"firstName" : "test first name",
	"lastName" : "test last name",
	"password" : "123",
	"email" : "test.user@gmail.com"
}
```
**Response**
```JSON
{
	"firstName" : "564e31d6-2d01-49f7-9c0f-29a8b38b2b27",
	"firstName" : "test first name",
	"lastName" : "test last name",
	"email" : "test.user@gmail.com",
	"vehicleList" : [
		{
			"id" : "b48eb457-2b50-419d-abee-7af50f4832e2",
			"name":"Mazda 2003","owner":"test first name test last name","maintenanceHistory":[]
		}
	]
}
```
- GET `/user/<userId>`: Get user data
**Response**
```JSON
{
	"firstName" : "test first name",
	"lastName" : "test last name",
	"password" : "123",
	"email" : "test.user@gmail.com"
}
```
- PATCH `/user/vehicles`: Add vehicle(s) for user(s).

**Request**: Map of user id and list of vehicle id.
```JSON
{
	"564e31d6-2d01-49f7-9c0f-29a8b38b2b27" : ["b48eb457-2b50-419d-abee-7af50f4832e2"]
}
```
**Response**
```JSON
{
	"firstName" : "564e31d6-2d01-49f7-9c0f-29a8b38b2b27",
	"firstName" : "test first name",
	"lastName" : "test last name",
	"email" : "test.user@gmail.com",
	"vehicleList" : [
		{
			"id":"b48eb457-2b50-419d-abee-7af50f4832e2",
			"name" : "Mazda 2003",
			"owner":"test first name test last name",
			"maintenanceHistory" : []
		}
	]
}
```
- PUT `/user/vehicles/owner`: Change vehicle(s) for user(s).

**Request**: Map of user id and list of vehicle id.
```JSON
{
	"fromOwnerId":"f06faf08-b5fd-47db-9641-552783efecb1",
	"toOwnerId":"45446ab8-1dc3-4799-86e3-3e69a2e70c26",
	"vehiclesList":["2e3cf90c-2e23-4072-b176-ffe8201dfb26"]
}
```
**Response**
```JSON
{
	"id":"45446ab8-1dc3-4799-86e3-3e69a2e70c26",
	"firstName":"2nd User",
	"lastName":"2nd User",
	"email":"2nd.user@gamil.com",
	"vehicleList":[
		{
			"id":"2e3cf90c-2e23-4072-b176-ffe8201dfb26",
			"name":"Mazda 2003",
			"owner":"2nd User 2nd User",
			"maintenanceHistory":[]
		}
	]
}
```
### Vehicle API(s)
- POST `/vehicle/register`

**Request**: all mandatory
```JSON
{
	"name":"Mazda 2003"
}
```
**Response**
```JSON
{
	"id":"b48eb457-2b50-419d-abee-7af50f4832e2",
	"name":"Mazda 2003",
	"owner":""
}
```
- GET `/vehicle/<vehicleId>`
**Response**
```JSON
{
	"id":"b48eb457-2b50-419d-abee-7af50f4832e2",
	"name":"Mazda 2003",
	"owner":""
}
```
### Maintenance record API(s)
- POST `/maintenance/<vehicleId>`: Add maintenance for one vehicle
**Request**:
```JSON
{
	"cost":10, //mandatory
	"comment":"First repair" //optional
}
```
**Response**
```JSON
{
	"id":"9271c838-38df-4210-9205-86cf1a55c506",
	"cost":10,
	"comment":"First repair",
	"maintenanceDate":"2020/12/03 21:12:42",
	"vehicleId":"0ee2b346-5e70-46fb-be78-5240a64bcecb"
}
```
- GET `/maintenance/<maintenanceId>`: Get maintenance record
**Response**
```JSON
{
	"id":"9271c838-38df-4210-9205-86cf1a55c506",
	"cost":10,
	"comment":"First repair",
	"maintenanceDate":"2020/12/03 21:12:42",
	"vehicleId":"0ee2b346-5e70-46fb-be78-5240a64bcecb"
}
```
- PUT `/maintenance/<maintenanceId>`: Edit maintenance record
**Request**:
```JSON
{
	"cost":100, //mandatory
	"comment":"New repair" //optional
}
```
**Response**
```JSON
{
	"id":"4f59b191-22b4-443a-b2fc-60dc504f55fa",
	"cost":100,
	"comment":"New repair",
	"maintenanceDate":"2020/12/03 21:12:42",
	"vehicleId":"0ee2b346-5e70-46fb-be78-5240a64bcecb"
}
```
- DELETE `/maintenance/<maintenanceId>`: Delete maintenance record
