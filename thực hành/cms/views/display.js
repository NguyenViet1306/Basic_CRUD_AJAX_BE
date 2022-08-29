getAllCustomer();
let idCustomer;

function getAllCustomer() {
    $.ajax({
        type: "GET",
        url: "http://localhost:8080/customers",
        success: function (data) {
            displayTable(data)
        }
    })
}

function displayTable(data) {
    let result = ""
    result += "<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\">"
    for (let i = 0; i < data.length; i++) {
        result += "<tr>"
        result += "<td>" + (i + 1) + "</td>"
        result += "<td>" + data[i].name + "</td>"
        result += "<td>" + data[i].age + "</td>"
        result += "<td>" + data[i].address + "</td>"
        result += "<td>" + data[i].department.name + "</td>"
        result += "<td><button type=\"button\" class=\"btn btn-danger\" onclick='updateForm(" + data[i].id + ")'>Update</button>"
        result += "<button type=\"button\" class=\"btn btn-danger\" onclick='deleteCustomer(" + data[i].id + ")'>Delete</button>"
        result += "</tr>"
    }
    result += "</table>"
    document.getElementById("list-customers").innerHTML = result;
}

function displayCreateForm() {
    $.ajax({
        type: "GET",
        url: "http://localhost:8080/customers/departments",
        success: function (data) {
            let result = ""
            for (let i = 0; i < data.length; i++) {
                result += "<option value='" + data[i].id + "'>" + data[i].name
                result += "</option>"
            }
            document.getElementById("departments").innerHTML = result;
        }
    })
    formCreate()
    document.getElementById("titleFrom").innerHTML = "Create new customer"
    document.getElementById("button").innerHTML = "Create"
    document.getElementById("button").setAttribute("onclick", "createCustomer()")
    // onclick="createHuman()"
    $('#myModal').modal('show');
}

function formCreate() {
    document.getElementById("name").value = ""
    document.getElementById("age").value = ""
    document.getElementById("address").value = ""
    document.getElementById("departments").value = ""
}


function createCustomer() {
    let name = $('#name').val()
    // let name =document.getElementById("name").value
    // let age =document.getElementById("age").value
    // let ađres =document.getElementById("address").value
    let age = $('#age').val()
    // // let phone = $('#phone').val()
    let address = $('#address').val()
    let room_id = $('#departments').val()
    console.log(name)
    console.log(age)
    console.log(address)
    console.log(room_id)
    let customer = {
        name: name,
        age: age,
        address: address,
        department:{
            id:room_id
        }
    };


    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        type: "POST",
        data: JSON.stringify(customer),
        //tên API
        url: "http://localhost:8080/customers",
        //xử lý khi thành công
        success: function () {
           formCreate()
            document.getElementById("messageCreate").innerHTML = "Create successfully!";
                    getAllCustomer();
        }
    });
    //chặn sự kiện mặc định của thẻ
    event.preventDefault();
}

function setFormUpdate(data) {
    document.getElementById("name").value = data.name
    document.getElementById("age").value = data.age
    document.getElementById("address").value = data.address
    // document.getElementById("department").value = data.department.id
}

function updateForm(id) {
    $.ajax({
        type: "GET",
        url: "http://localhost:8080/customers/departments",
        success: function (data) {
            let result = ""
            for (let i = 0; i < data.length; i++) {
                result += "<option value='" + data[i].id + "'>" + data[i].name
                result += "</option>"
            }
            document.getElementById("departments").innerHTML = result;
        }
    })
    $.ajax({
        type: "GET",
        url: "http://localhost:8080/customers/" + id,
        success: function (data) {
            idCustomer = id
            setFormUpdate(data)
            document.getElementById("messageCreate").innerHTML = ""
        }
    })
    // document.getElementById("titleFrom").innerHTML = "Update customer"
    document.getElementById("button").innerHTML = "Update"
    document.getElementById("button").setAttribute("onclick", "updateCustomer()")
    $('#myModal').modal("show")
}

function updateCustomer() {
    let name = $('#name').val()
    let age = $('#age').val()
    let address = $('#address').val()
    let department_id = $('#departments').val()

    let customer = {
        id: idCustomer,
        name: name,
        age: age,
        address: address,
        department: {
            id: department_id,
        }
    }

    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        type: "POST",
        url: "http://localhost:8080/customers",
        data: JSON.stringify(customer),
        success: function (data) {
            setFormUpdate(data)
            document.getElementById("messageCreate").innerHTML = "Update successfully!"
            getAllCustomer()
        }
    })
    event.preventDefault()
}


function deleteForm(id) {
    idCustomer = id
    document.getElementById("messageDelete").innerHTML = ""
   $('deleteModal').modal("show")
}

function deleteCustomer(id) {
    $.ajax({
        type: "DELETE",
        url: "http://localhost:8080/customers/" + id,
        success: function () {
            document.getElementById("messageDelete").innerHTML = "Delete successfully!"
            getAllCustomer()
        }
    })
    event.preventDefault()
}