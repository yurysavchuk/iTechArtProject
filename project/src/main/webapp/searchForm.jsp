<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
    <link href="css/searchContact.css" type="text/css" rel="stylesheet">
    <link href="css/bootstrap.css" type="text/css" rel="stylesheet">
    <link href="css/bootstrap-theme.css" type="text/css" rel="stylesheet">
    <meta charset="utf-8"/>
</head>
<body>
<div id="wrapper">
    <div id="header">
        <p>Search contacts</p>
    </div>
    <div class="linkDiv">
        <p>
            <a href="viewAllContacts.do">Contacts list</a>
            <a href="searchForm.jsp">Search contact</a>
        </p>
    </div>
    <div id="content">
        <form action="search.do" method="post">
            <div>
                <p>
                    <label>Surname</label>
                    <input type="text" name="surname" class="inp form-control">
                </p>

                <p>
                    <label>Name</label>
                    <input type="text" name="name" class="inp form-control">
                </p>

                <p>
                    <label>Middle name</label>
                    <input type="text" name="mName" class="inp form-control">
                </p>

                <p>
                    <label>Birthdate</label>
                    <input type="text" name="bDay" class="inp form-control">
                </p>

                <p>
                    <label>Sex</label>
                    <select size="1" name="sex" class="inp form-control" value="">
                        <option value=""></option>
                        <option value="male">male</option>
                        <option value="female">female</option>
                    </select>
                </p>

                <p>
                    <label>Marital status</label>
                    <input type="text" name="mrtlStat" class="inp form-control">
                </p>

                <p>
                    <label>Nationality</label>
                    <input type="text" name="national" class="inp form-control">
                </p>

                <p>
                    <label>Address</label>
                </p>

                <p>
                    <label class="adrLbl">Country</label>
                    <input type="text" name="country" class="address form-control">
                </p>

                <p>
                    <label class="adrLbl">City</label>
                    <input type="text" name="city" class="address form-control">
                </p>

                <p>
                    <label class="adrLbl">Street/House/Flat</label>
                    <input type="text" name="address " class="address form-control">
                </p>

                <p>
                    <label class="adrLbl">Index</label>
                    <input type="text" name="index" class="address form-control">
                </p>
            </div>
            <div>
                <p>
                    <input type="submit" value="search" class="btn btn-success">
                </p>
            </div>
        </form>
    </div>

    <div id="footer"></div>

</div>
</body>
</html>

