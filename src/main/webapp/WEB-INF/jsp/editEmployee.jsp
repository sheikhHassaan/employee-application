<%@ taglib prefix="s" uri="/struts-tags" %>
    <!DOCTYPE html>
    <html lang="en-US">

    <head>
        <title>Edit Employee</title>
    </head>

    <body>
        <h2>Edit Employee</h2>

        <s:form action="updateEmployee" method="post">

            <s:select name="managerId" list="managers" listKey="id" listValue="firstName + ' ' + lastName"
                            label="Reports To" />

            <s:hidden name="employee.id" />
            <s:textfield name="employee.firstName" label="First Name" />
            <s:textfield name="employee.lastName" label="Last Name" />
            <s:textfield name="employee.address" label="Address" />
            <s:textfield name="employee.email" label="Email" />
            <s:textfield name="employee.phoneNumber" label="Phone Number" />
            <s:textfield name="employee.department" label="Department" />
            <s:textfield name="employee.designation" label="Designation" />
            <s:textfield name="employee.salary" label="Salary" />
            <s:textfield name="employee.experience" label="Experience" />

            <s:submit value="Update Employee" />
            <s:a action="listEmployees">Cancel</s:a>
        </s:form>
    </body>

    </html>