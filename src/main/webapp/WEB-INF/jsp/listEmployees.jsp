<%@ taglib prefix="s" uri="/struts-tags" %>
    <!DOCTYPE html>
    <html lang="en-US">

    <head>
        <title>Employee List</title>
    </head>

    <body>
        <h2>Employees</h2>
        <table border="1" cellpadding="5" cellspacing="0">
            <thead>
                <tr>
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
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <s:iterator value="employees">
                    <tr>
                        <td style="display:none;">
                            <s:property value="id" />
                        </td>
                        <td>
                            <s:property value="firstName" />
                        </td>
                        <td>
                            <s:property value="lastName" />
                        </td>
                        <td>
                            <s:property value="address" />
                        </td>
                        <td>
                            <s:property value="email" />
                        </td>
                        <td>
                            <s:property value="phoneNumber" />
                        </td>
                        <td>
                            <s:property value="department" />
                        </td>
                        <td>
                            <s:property value="designation" />
                        </td>
                        <td>
                            <s:property
                                value="reportsTo != null ? reportsTo.firstName + ' ' + reportsTo.lastName : ''" />
                        </td>
                        <td>
                            <s:property value="salary" />
                        </td>
                        <td>
                            <s:property value="experience" />
                        </td>
                        <td>
                            <button type="button"
                                    onclick="window.location.href='<s:url action="editEmployee"><s:param name="id" value="id"/></s:url>'">
                                Edit
                            </button>

                            <button type="button"
                                    onclick="window.location.href='<s:url action="deleteEmployee"><s:param name="id" value="id"/></s:url>'">
                                Delete
                            </button>
                        </td>
                    </tr>
                </s:iterator>
            </tbody>
        </table>

        <br />
        <button type="button" onclick="window.location.href='addEmployee'">Add Employee</button>
    </body>

    </html>