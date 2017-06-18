# moneytransfer
Simple dropwizard demo, implements a RESTful API to move money from one account to another


-------------------------------------------------

```
POST /api/transfer
```

Move money from source_account_id to destination_account_id

- source_account_id must be different from destination_account_id
- source_account_id must have enough fund to perform the transfer
- returns a a new transfer object with a UUID as transfer id

Example:

```
POST http://localhost:8080/api/transfer
request: {"source_account_id":1, "destination_account_id":2, "amount":"23"}
response:  {"id":"5e0ea8d1-039f-490b-b3f6-d06c58a86803","source_account_id":"1","destination_account_id":"2","amount":23}
```

```
GET /api/transfer/{:id}
```

Gets details of a an existent transfer

Example:

```
GET http://localhost:8080/api/transfer/5e0ea8d1-039f-490b-b3f6-d06c58a86803
response:  {"id":"5e0ea8d1-039f-490b-b3f6-d06c58a86803","source_account_id":"1","destination_account_id":"2","amount":23}
```

```
GET /api/account/{:id}/transfers
```

Gets the list of transfer where account :id is either source or destination

Example:

```
GET http://localhost:8080/api/account/1/transfers
response: [
  {
    "id": "5e0ea8d1-039f-490b-b3f6-d06c58a86803",
    "source_account_id": "1",
    "destination_account_id": "2",
    "amount": 23
  },
  {
    "id": "98487965-6397-40d5-ab1a-f07d59c52097",
    "source_account_id": "1",
    "destination_account_id": "2",
    "amount": 50
  },
  {
    "id": "73e0e273-ecdf-4cf1-9ffb-064d6e1dab1c",
    "source_account_id": "2",
    "destination_account_id": "1",
    "amount": 12
  }
]
```

```
GET  /api/autotest
```

The purpouse of this resource is just to show how the client class can work. It basically calls the service itself and perform a simple transfer using the MoneyTransferClient class from account 1 to account 2.

Returns the list of transfer of account 1

------------------------------------------------------------

*A couple of notes*:

- Fund transfers are performed by MoneyService class that uses AccountDAO and TransferDAO classes. To be noted that the transfer is performed within a single jdbi transaction so either everything is committed or everything is rolled back as required by this use case.
- In the current design the transfer is immediately performed without any confirmation. This API design is a little simplistic; a better one could have been using the so called "Try-Confirm/Cancel" pattern. 
In this case transfer object has a status, for example "new", "committed" or "aborted". The transfer object is created with the POST verb in "new" status. The client then must acknowledge the transfer before a certain amount of time, issuing a PUT to the transfer id. A this point the fund transfer is actually performed and the transfer is marked as "committed". 
If something goes wrong or the timeout expires the transfer is marked as "aborted", everything is rolled back and so accounts status doesn't change. 
There are a couple of good properties here: first of all the POST API creates every time a different transfer object (with different id) that works as a "booking" for the actual transfer. Second the PUT API will be idempotent so committing two or more times the same transfer wouldn't perform the transaction more than once. This design makes the system more protected to issues like accidentally sending multiple time the same transfer.



Setup postgresql database with Docker
-----

Download docker from https://www.docker.com/community-edition for your operating system.

Use Docker Postgres Image to startup a database:

https://hub.docker.com/_/postgres/

Downloading postgres container command:

```
docker pull postgres
```

Starting postgres container command:

```
docker run --name moneytransfer_postgres -p 5432:5432 -e POSTGRES_PASSWORD=moneytransfer -e POSTGRES_USER=moneytransfer -e POSTGRES_DB=moneytransfer postgres
```

