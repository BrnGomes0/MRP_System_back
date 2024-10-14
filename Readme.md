# Documentation API - MRP Bosch

BaseUrl: `http://10.234.89.187:8080`

## Material:

### POST material:

Endpoint: `/material`

Type: JSON

Attributes:
~~~json
{
  "materialCode": "Integer",
  "demand": "Integer",
  "inicialInventory":"Integer"
}	
~~~

Return:
~~~json
{
  "idMaterial": "Long",
  "materialCode": "Integer",
  "demand": "Integer",
  "inicialInventory": "Integer"
}
~~~

### GET all materials:

Endpoint: `/material/materials`

Return:

~~~json
[
	{
		"idMaterial": "Long",
		"materialCode": "Integer",
		"demand": "Integer",
		"inicialInventory": "Integer"
	}
]
~~~

### GET by ID:

Endpoint: `/material/{material_id}`

Return:

~~~json
{
	"idMaterial": "Long",
	"materialCode": "Integer",
	"demand": "Integer",
	"inicialInventory": "Integer"
}
~~~

### DELETE material:

Endpoint: `/material/delete_registered_item_by_id/{material_id}`

Return:

~~~json
"ID Record Info: {material id} deleted successfully!"
~~~


## InfoRecord:

### POST inforecord:

Endpoint: `/inforecord`

Type: JSON

Attributes:
~~~json
{
  "price": "Integer",
  "leadTime": "Integer"
}
~~~

Return:
~~~json
{
  "idInfoRecord": "Long",
  "materialCode": "Integer",
  "supplierCode": "Integer",
  "materialText": "String",
  "price": "Integer",
  "leadTime": "Integer"
}
~~~

### GET all inforecord:

Endpoint: `/inforecord/inforecord`

Return:

~~~json
[
  {
    "idInfoRecord": "Long",
    "materialCode": "Integer",
    "supplierCode": "Integer",
    "price": "Integer",
    "leadTime": "Integer"
  }
]
~~~

### GET by ID:

Endpoint: `/inforecord/{inforecord_id}`

Return:

~~~json
{
  "idInfoRecord": "Long",
  "materialCode": "Integer",
  "supplierCode": "Integer",
  "price": "Integer",
  "leadTime": "Integer"
}
~~~

### DELETE inforecord:

Endpoint: `/inforecord/delete_infoRecord_by_id/{inforecord_id}`

Return:

~~~json
"InfoRecord ID: {inforecord_id} successfully deleted!"
~~~


## Inventory:

### POST inventory:

Endpoint: `/inventory/register/{material_id}`

Type: JSON

Return:
~~~json
{
  "id": "Long",
  "week": "Integer",
  "materialName": "String",
  "demand": "Integer",
  "quantityInInventory": "Integer"
}
~~~

### GET all inventories:

Endpoint: `/inforecord/all`

Return:

~~~json
[
  {
    "inventory_id": "Long",
    "week": "Integer",
    "materialName": "String",
    "demand": "Integer",
    "stockQuantity": "Integer"
  }
]
~~~


## Purchase Order:

### POST purchase order:

Endpoint: `/purchaseOrder/{inventory_id}`

Type: JSON

Return:
~~~json
{   "purchase_id": "Long",  
    "week": "Integer",  
    "material": "String",  
    "orderPlaced": "Integer",  
    "orderReceived": "Integer"
}
~~~

### GET all purchase order:

Endpoint: `/purchaseOrder/updatePurchasingOrder/{inventory_id}`

Attributes:
~~~json
{
	"demand": "Integer",
	"orderReceived": "Integer"
}
~~~

Return:

~~~json
{
  "purchasing_id": "Long",
  "week": "Integer",
  "orderReceived": "Integer",
  "orderPlaced": "Integer",
  "materialName": "String"
}
~~~

### GET all purchase order (Material A):

Endpoint: `/purchaseOrder/allMaterialsA


Return:

~~~json
[
  {
    "purchasing_id": "Long",
    "week": "Integer",
    "orderPlaced": "Integer",
    "orderReceived": "Integer",
    "demand": "Integer",
    "materialName": "String"
  }
]
~~~

### GET all purchase order (Material B):

Endpoint: `/purchaseOrder/allMaterialsB


Return:

~~~json
[
  {
    "purchasing_id": "Long",
    "week": "Integer",
    "orderPlaced": "Integer",
    "orderReceived": "Integer",
    "demand": "Integer",
    "materialName": "String"
  }
]
~~~

