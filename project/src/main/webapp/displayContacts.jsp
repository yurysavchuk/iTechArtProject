<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%--

--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
    <link href="css/index.css" type="text/css" rel="stylesheet">
    <link href="css/bootstrap.css" type="text/css" rel="stylesheet">
    <link href="css/bootstrap-theme.css" type="text/css" rel="stylesheet">
    <meta charset="utf-8"/>
    <script src="js/indexScript.js"></script>
</head>
<body>
<div id="wrapper" class="indexWrap">
    <div id="header">
        <p>List contacts</p>
    </div>
    <div id="content" class="index">
        <form action="deleteContact.do" id="formList">
            <div class="divPagination">
                <c:if test="${currentPage != 1}">
                    <a href="viewAllContacts.do?page=${currentPage - 1}">
                        <input type="button" value="Previous" class="btn btn-success">
                    </a>
                </c:if>

                <c:if test="${currentPage lt noOfPages}">
                    <a href="viewAllContacts.do?page=${currentPage + 1}">
                        <input value="Next" type="button" class="btn btn-success">
                    </a>
                </c:if>
            </div>

            <div id="tableId">

                <table id="contacts" class="table table-hover table-condensed">
                    <tr>
                        <td></td>
                        <td>Полное имя</td>
                        <td>Дата рождения</td>
                        <td>Адрес</td>
                        <td>Компания</td>
                    </tr>
                    <c:forEach var="contact" items="${contacts}">
                        <tr>
                            <td><input type="checkbox" value="${contact.id}" name="idContact"></td>
                            <td>

                                <a href="edit.do?id=${contact.id}">${contact.surname} ${contact.name} ${contact.midleName}</a>

                            </td>
                            <td>
                                    ${contact.birthday}
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${ empty contact.address.country and empty
                                    contact.address.city}">
                                        ${contact.address.street}
                                    </c:when>
                                    <c:otherwise>
                                        ${contact.address.country},
                                        ${contact.address.city},
                                        ${contact.address.street},
                                        ${contact.address.house},
                                        ${contact.address.flat}
                                    </c:otherwise>

                                </c:choose>
                            </td>
                            <td>
                                    ${contact.curWorkplace}
                            </td>
                        </tr>
                    </c:forEach>

                </table>
                <p id="errorMessage"><span class="error-mess"></span></p>

            </div>


            <div class="buttons">
                <p>

                    <input type="button" value="Create contact" class="btn btn-success"
                           onclick="location.href='createNewContact.do';">
                    <input type="submit" value="Edit" class="btn btn-success" name="submit">
                    <input type="submit" value="Delete" class="btn btn-success" name="submit">
                    <input type="button" value="Search contact" class="btn btn-success"
                           onclick="location.href='searchForm.jsp';">
                    <input type="submit" value="Send email" class="btn btn-success" name="submit" id="sendEmail">
                </p>
            </div>
        </form>
    </div>

    <div id="footer"></div>
</div>

</body>
</html>





