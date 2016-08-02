<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!doctype html>
<html lang="en" ng-app="crud">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>CRUD Users</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/crud-resources/css/bootstrap.min.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/crud-resources/css/crud.css" />
	<script type="text/javascript" src="${pageContext.request.contextPath}/crud-resources/js/angular.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/crud-resources/js/crudapp.js"></script>
</head>
<body ng-controller="MainController as main">
<div class="nav-center">
<section class="tab">
	<ul class="nav nav-pills">
		<li ng-class="{active:main.tab === 1}">
			<a href ng-click="main.setTab(1)">Find by ID</a></li>
		<li ng-class="{active:main.tab === 2}">
			<a href ng-click="main.setTab(2)">Find by name</a></li>
		<li ng-class="{active:main.tab === 3}">
			<a href ng-click="main.setTab(3)">Find by age range</a></li>
		<li ng-class="{active:main.tab === 4}">
			<a href ng-click="main.setTab(4)">Find by date range</a></li>
        <li ng-class="{active:main.tab === 5}">
            <a href ng-click="main.setTab(5)">Find by admin access</a></li>
		<li ng-class="{active:main.tab === 6}">
			<a href ng-click="main.setTab(6)">Add user</a></li>
        <li><button class="btn btn-default" ng-click="setFlag(1)">Show all</button></li>
	</ul>
	<div ng-show="main.isSet(1)">
		<form name="id" ng-submit="id.$valid&&setFlag(2)" novalidate class="form-inline">
            <div class="form-group">
			<input type="number" ng-model="findid" placeholder="Enter ID" min=1 max=99999999 required class="form-control">
            <input type="submit" class="btn" value="Search" />
            </div>
			<h5 class="alert alert-info">ID should be in range 1-99999999 </h5>
		</form>
	</div>
	<div ng-show="main.isSet(2)">
		<form name="name" ng-submit="name.$valid&&setFlag(3)" novalidate class="form-inline">
            <div class="form-group">
			<input type="text" ng-model="findname" placeholder="Enter name" ng-maxlength='25' pattern="^\w+$" required class="form-control">
                <label>Exact match</label><span> </span><input type="checkbox" ng-model="like">
            <input type="submit" class="btn" value="Search" />
            </div>
			<h5 class="alert alert-info">Name should contain only alphanumeric characters, max length 25</h5>
		</form>
	</div>
	<div ng-show="main.isSet(3)">
		<form name="age" ng-submit="age.$valid&&setFlag(4)" novalidate class="form-inline">
            <div class="form-group">
			<input type="number" ng-model="agestart" placeholder="Enter age" min=1 max=120 required class="form-control">
            <input type="number" ng-model="ageend" placeholder="Enter age" min=1 max=120 required class="form-control">
            <input type="submit" class="btn" value="Search" />
            </div>
			<h5 class="alert alert-info">Age in range 1-120. Humans usually live less then 120 years </h5>
		</form>
	</div>
	<div ng-show="main.isSet(4)">
		<form name="date" ng-submit="date.$valid&&setFlag(5)" novalidate class="form-inline">
            <div class="form-group">
			<input type="datetime-local" ng-model="datestart" min="2001-01-01T00:00:00" placeholder="Enter start date" required class="form-control">
            <input type="datetime-local" ng-model="dateend" min="2001-01-01T00:00:00" placeholder="Enter end date" required class="form-control">
            <input type="submit" class="btn" value="Search" />
            </div>
            <h5 class="alert alert-info"> Date format [yyyy-mm-ddThh:mm:ss]. Example: 2016-08-01T21:01:33 </h5>
		</form>
	</div>
    <div ng-show="main.isSet(5)">
        <form name="odmin" ng-submit="setFlag(6)" novalidate>
            <label>ADMIN </label>
            <input type="checkbox" ng-model="adm">
            <input type="submit" class="btn" value="Search" />
            <h5 class="alert alert-info"> "Java is to Javascript as a fun is to funeral" </h5>
        </form>
    </div>
	<div ng-show="main.isSet(6)">
		<form name="adduser" ng-submit="adduser.$valid&&main.addUser()" novalidate class="form-inline">
            <div class="form-group">
			<input type="text" ng-model="addname" placeholder="Enter name" ng-maxlength='25' pattern="^\w+$" required class="form-control">
			<input type="number" ng-model="addage" placeholder="Enter age" min=1 max=120 required class="form-control">
			<label>ADMIN <input type="checkbox" ng-model="isadmin"></label>
            <input type="submit" class="btn" value="Add" />
            </div>
			<h5 class="alert alert-info">ID and current date sets automatically, name should contain only alphanumeric characters, age range 1-120 </h5>
		</form>
	</div>
</section>
</div>
<table class="table table-bordered">
		<tr>
			<th width="11%">ID</th>
			<th width="19%">NAME</th>
			<th width="10%">AGE</th>
			<th width="10%">ADMIN</th>
			<th width="25%">CREATED DATE</th>
			<th width="25%">CONTROL</th>
		</tr>
		<tr ng-repeat="user in usersarray" ng-class="{warning:user.isChanged === 1, success: user.isChanged === 2}">
			<td>{{user.id}}</td>
			<td><a href ng-click="main.setName(user)">{{user.name}}</a></td>
			<td><a href ng-click="main.setAge(user)">{{user.age}}</a></td>
			<td><a href ng-click="main.swap(user)">{{user.admin}}</a></td>
			<td>{{user.createdDate | date:'MM/dd/yyyy @ h:mma'}}</td>
			<td align="center">
				<button class="btn-warning" ng-click="main.updateUser(user)">Save changes</button>
				<button class="btn-danger" ng-click="main.deleteUser(user.id)"> Delete </button>
			</td>
		</tr>
		<tr>
			<td align="center" colspan="6">
				<ul class="pagination pagination-sm">
                    <li class="ppage" ng-class="main.prevPageDisabled()">
                        <a href ng-click="main.prevPage()">< Prev</a></li>
                    <li ng-repeat="n in range()" ng-class="{active: n == currentPage}" ng-click="main.setPage(n)">
                        <a href >{{n+1}}</a></li>
                    <li class="npage" ng-class="main.nextPageDisabled()">
                        <a href ng-click="main.nextPage()">Next ></a></li>
				</ul>
			</td>
		</tr>
</table>
</body>
</html>