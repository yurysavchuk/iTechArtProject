<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head>
    <title></title>
    <link href="css/emailForm.css" type="text/css" rel="stylesheet">
    <link href="css/bootstrap.css" type="text/css" rel="stylesheet">
    <link href="css/bootstrap-theme.css" type="text/css" rel="stylesheet">
    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=\"ISO-8859-1\"/>
    <script src="js/emailScript.js"></script>
</head>
<body>
<div id="wrapper">
    <div id="header">
        <p>Send email</p>
    </div>

    <div class="linkDiv">
        <p>
            <a href="viewAllContacts.do">Contacts list</a>
            <a href="searchForm.jsp">Search contact</a>
        </p>
    </div>
    <div id="content">
        <div class="resultMessage">
            <p>${result}</p>
        </div>
        <form action="sendEmail.do" method="post" id="emailForm">
            <div class="inputElements">
                <label>Emails</label>

                <div class="recipientsDiv">
                    <c:forEach var="contact" items="${contacts}">
                        <p>
                            <input type="hidden" value="${contact.id}" name="id">
                            <span name="idEmail">${contact.email}</span>
                        </p>
                    </c:forEach>
                </div>
                <p>
                    <label>Theme</label>
                    <input type="text" name="theme" class="inp form-control" value="${theme}">
                </p>

                <p>
                    <label>Template</label>
                    <select name="template" size="1"
                            onchange="with (this) document.location.href=options [selectedIndex].value"
                            class="inp form-control">

                        <c:forEach var="template" items="${templateNames}">


                            <option value="templateChange.do?template=${template}&&id=${id_contacts}">
                                    ${template}
                            </option>

                        </c:forEach>
                    </select>
                </p>


                <p>
                    <label>Content</label>
                </p>
                <textarea name="text" class="textArea">
                    ${template}
                </textarea>
            </div>
            <div>
                <p id="errorMessage">
                    <span class="error-message"></span>
                </p>

                <p>
                    <input type="submit" value="send" class="btn btn-success">
                    <a href="viewAllContacts.do">
                        <input type="button" value="cancel" class="btn btn-success">
                    </a>
                </p>
            </div>
        </form>
    </div>

    <div id="footer"></div>

</div>

</body>
</html>

