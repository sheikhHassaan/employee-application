<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html lang="en-US">
<head>
    <title>Add New Employee</title>
    <style>
        form {
            width: 400px;
            margin: 20px auto;
            padding: 20px;
            border: 1px solid #ddd;
            border-radius: 5px;
            background: #f9f9f9;
        }
        label {
            display: block;
            margin-top: 10px;
            font-weight: bold;
        }
        input, select {
            width: 100%;
            padding: 8px;
            margin-top: 5px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }
        .btn {
            margin-top: 15px;
            padding: 10px;
            width: 48%;
            border: none;
            cursor: pointer;
            color: white;
            border-radius: 4px;
        }
        .btn-save {
            background-color: #4CAF50;
        }
        .btn-cancel {
            background-color: #f44336;
        }
    </style>
</head>
<body>
    <h2 style="text-align:center;">Add New Employee</h2>

    <s:form action="addEmployee" method="post">

        <label>First Name:</label>
        <s:textfield name="firstName" />

        <label>Last Name:</label>
        <s:textfield name="lastName" />

        <label>Address:</label>
        <s:textfield name="address" />

        <label>Email:</label>
        <s:textfield name="email" />

        <label>Phone Number:</label>
        <s:textfield name="phoneNumber" />

        <label>Department:</label>
        <s:textfield name="department" />

        <label>Designation:</label>
        <s:textfield name="designation" />

        <label>Reports To:</label>
        <s:select
            name="reportsTo"
            list="managers"
            listKey="id"
            listValue="firstName + ' ' + lastName" />

        <label>Salary:</label>
        <s:textfield name="salary" />

        <label>Experience:</label>
        <s:textfield name="experience" />

        <div style="display:flex; justify-content:space-between;">
            <input type="submit" value="Add Employee" class="btn btn-save" />
            <a href="listEmployees" class="btn btn-cancel" style="text-align:center; line-height:30px;">Cancel</a>
        </div>
    </s:form>
</body>
</html>