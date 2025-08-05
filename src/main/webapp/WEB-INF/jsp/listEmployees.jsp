<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html lang="en-US">
<head>
    <title>Employee List</title>
    <style>
        table {
            width: 100%;
            border-collapse: collapse;
        }
        th, td {
            padding: 8px;
            text-align: left;
            border: 1px solid #ddd;
        }
        th {
            background-color: #f2f2f2;
        }
        .btn {
            padding: 5px 10px;
            text-decoration: none;
            border-radius: 4px;
            color: white;
        }
        .btn-edit {
            background-color: #4CAF50;
        }
        .btn-delete {
            background-color: #f44336;
        }
        .btn-add {
            background-color: #2196F3;
            padding: 8px 15px;
            margin-top: 10px;
            display: inline-block;
        }
    </style>
    <script>
        function confirmDeleteEmployee(empId) {
            if (confirm("Are you sure you want to delete this employee?")) {
                window.location.href = 'deleteEmployee?id=' + empId;
            }
        }
    </script>
</head>
<body>
    <h2 style="text-align:center;">Employees</h2>
    <table border="1" cellpadding="5" cellspacing="0">
        <thead>
            <tr>
                <th>ID</th>
                <th>FirstName</th>
                <th>LastName</th>
                <th>Address</th>
                <th>Email</th>
                <th>Phone</th>
                <th>Department</th>
                <th>Designation</th>
                <th>ReportsTo</th>
                <th>Salary</th>
                <th>Experience</th>
            </tr>
        </thead>
        <tbody>
            <s:iterator value="employees">
                <tr>
                    <td style="display:none;"><s:property value="id"/></td>
                    <td><s:property value="firstName"/></td>
                    <td><s:property value="lastName"/></td>
                    <td><s:property value="address"/></td>
                    <td><s:property value="email"/></td>
                    <td><s:property value="phoneNumber"/></td>
                    <td><s:property value="department"/></td>
                    <td><s:property value="designation"/></td>
                    <td><s:property value="reportsTo != null ? reportsTo.firstName + ' ' + reportsTo.lastName : 'N/A'"/></td>
                    <td><s:property value="salary"/></td>
                    <td><s:property value="experience"/></td>
                    <td>
                        <a class="btn btn-edit" href="editEmployee?id=<s:property value='id'/>">Edit</a>
                        <a class="btn btn-delete" href="javascript:void(0);" onclick="confirmDeleteEmployee(<s:property value='id'/>)">Delete</a>
                    </td>
                </tr>
            </s:iterator>
        </tbody>
    </table>

    <br/>
    <a class="btn btn-add" href="addEmployee">Add Employee</a>

</body>
</html>