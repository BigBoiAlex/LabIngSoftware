<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:pageTemplate pageTitle="EditCar">
    <h1>Edit a Car</h1>
    <div class="col-md-8 order-md-1">
        <form class="needs-validation" novalidate method="post" action="${pageContext.request.contextPath}/Cars/Update">
            <div class="row">
                <div class="col-md-6 mb-3">
                    <label for="license_plate">License plate</label>
                    <input type="text" class="form-control" id="license_plate" name="license_plate"
                           placeholder="License plate" required value="${car.licensePlate}">
                    <div class="invalid-feedback" style="width: 100%">
                        License Plate is required.
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="col-md-6 mb-3">
                    <label for="parking_spot">Parking spot</label>
                    <input type="text" class="form-control" id="parking_spot" name="parking_spot"
                           placeholder="Parking Spot" required value="${car.parkingSpot}">
                    <div class="invalid-feedback" style="width: 100%">
                        Parking spot is required.
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="col-md-6 mb-3">
                    <label for="owner_id">Owner</label>
                    <select class="custom-select d-block w-100" id="owner_id" name="owner_id" required>
                        <option value="">Choose...</option>
                        <c:forEach var="user" items="${users}" varStatus="status">
                            <option value="${user.id}"${car.username eq user.username  ? 'selected' : ''}>${user.username}</option>
                        </c:forEach>
                    </select>
                    <div class="invalid-feedback">
                        Owner id is required.
                    </div>
                </div>
            </div>
            <input type="hidden" name="car_id" value="${car.id}"/>
            <hr class="mb-4">
            <button class="btn btn-primary btn-lg btn-block" type="submit">Save</button>

        </form>
    </div>
</t:pageTemplate>
