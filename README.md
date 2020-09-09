# decnet

<p align="center">
  <img src="https://img.shields.io/github/license/materna-se/decnet.svg?style=flat-square">
  <img src="https://img.shields.io/circleci/build/github/materna-se/decnet.svg?style=flat-square">
  <img src="https://img.shields.io/github/v/release/materna-se/decnet?style=flat-square">
</p>

decnet is an RESTful execution server for decision models powered by jDEC.

## Download

Artifacts of decnet are published in regular intervals to the releases section of GitHub. They can be seen at https://github.com/materna-se/decnet/releases.

## Documentation

### `GET /{namespace}`

Returns the decision model.

#### Request

##### Request Parameters

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|namespace|path|string|true|none|

#### Responses

|Status|Meaning|Description|
|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|The model is returned.|
|404|[Not Found](https://tools.ietf.org/html/rfc7231#section-6.5.4)|The model was not found.|

### `POST /{namespace}`

Executes the decision model.

#### Request

##### Request Parameters

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|body|body|object|false|none|
|namespace|path|string|true|none|

##### Example Request

```json
{
  "Employment Status": "UNEMPLOYED"
}
```

#### Responses

|Status|Meaning|Description|
|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|The result of the model execution.|
|404|[Not Found](https://tools.ietf.org/html/rfc7231#section-6.5.4)|The model was not found.|

##### Response Schema

Status Code `200`

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|outputs|object|false|none|none|
|context|object|false|none|none|
|messages|[string]|false|none|none|

##### Example Response

The result of the model execution.

```json
{
  "outputs": {
    "Employment Status Statement": "You are UNEMPLOYED"
  },
  "context": {
    "Employment Status Statement": {}
  },
  "messages": []
}
```

### `PUT /{namespace}`

Imports the decision model.

#### Request

##### Request Parameters

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|namespace|path|string|true|none|

#### Responses

|Status|Meaning|Description|
|---|---|---|
|204|[No Content](https://tools.ietf.org/html/rfc7231#section-6.3.5)|The model was imported successfully.|

##### Response Schema

Status Code `204`

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|messages|[string]|false|none|Warnings that occurred during compilation.|

##### Example Response

```json
{
  "messages": [
    "DMN: Missing expression for Decision Node 'Employment Status Statement'."
  ]
}
```

`DELETE /{namespace}`

Deletes the decision model.

#### Request

##### Request Parameters

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|namespace|path|string|true|none|

#### Responses

|Status|Meaning|Description|
|---|---|---|---|
|204|[No Content](https://tools.ietf.org/html/rfc7231#section-6.3.5)|The model was deleted successfully.|

### `GET /{namespace}/structure`

#### Request

##### Request Parameters

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|namespace|path|string|true|none|

#### Responses

|Status|Meaning|Description|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|Returns the input structure that is required for executing the decision model.|
|404|[Not Found](https://tools.ietf.org/html/rfc7231#section-6.5.4)|The model was not found.|

##### Example Response

Returns the input structure that is required for executing the decision model.

```json
{
  "Employment Status": {
    "type": "string",
    "options": [
      "UNEMPLOYED",
      "EMPLOYED",
      "SELF-EMPLOYED",
      "STUDENT"
    ]
  }
}
```