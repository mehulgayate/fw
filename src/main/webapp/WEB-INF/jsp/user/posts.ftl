<!DOCTYPE html>
<!--
Design by TEMPLATED
http://templated.co
Released for free under the Creative Commons Attribution License

Name       : RedMarket 
Description: A two-column, fixed-width design with dark color scheme.
Version    : 1.0
Released   : 20140101

-->
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<meta name="keywords" content="" />
<meta name="description" content="" />
<link href="http://fonts.googleapis.com/css?family=Source+Sans+Pro:200,300,400,600,700,900" rel="stylesheet" />
<link href="/static/user/default.css" rel="stylesheet" type="text/css" media="all" />
<link href="/static/user/fonts.css" rel="stylesheet" type="text/css" media="all" />

<!--[if IE 6]><link href="default_ie6.css" rel="stylesheet" type="text/css" /><![endif]-->

</head>
<body>
<div id="header-wrapper">
	<div id="header" class="container">
		<div id="logo">
			<h1><a href="#">FilteredWall</a></h1>
		</div>
		<div id="menu">
			<ul>
				<li><a href="/" accesskey="1" title="">Post Something</a></li>
				<li class="active"><a href="#" accesskey="1" title="">View Posts </a></li>
				<li><a href="/logout" accesskey="1" title="">Logout </a></li>
				
			</ul>
		</div>
	</div>
	<div id="banner" class="container">
		<div>
			<#if posts?has_content && posts?size !=0>
			<#list posts as post>			
			<div style="display:block; width: 900px; background: white; margin: 0 auto; padding: 30px; opacity: 0.6; margin-bottom: 15px;">
				<div style="display: inline-block; width: 300px;">
					${post.postText?if_exists}
				</div>
				<div style="display: inline-block; width: 100px; border-left: 2px solid; padding-left	: 2px;">
					${post.status?if_exists}
				</div>
				<#if post.taggedBy?has_content>
				<div style="display: inline-block; width: 250px; border-left: 2px solid; padding-left	: 2px;">
					Tagged By : ${post.taggedBy.name?if_exists}
				</div>
				</#if>
				<#if post.status=="NOT_VERIFIED">
					<div style="display: inline-block; width: 180px; border-left: 2px solid; padding-left	: 2px;">
						<a href="/verify-post?id=${post.id}">Verify</a>
						<a href="/remove-post?id=${post.id}" style="padding-left: 20px;">Remove</a>
					</div>
				</#if>
				
				<#if post.fileAttachment?has_content>
				<br/>
				<br/>
					<#if post.fileAttachment.fileType=="IMAGE">
						<img src="/file-view?id=${post.fileAttachment.id}" style="max-width: 500px"/>
					</#if>
					<#if post.fileAttachment.fileType=="VIDEO">
						<video style="max-width: 500px" controls>
						<source src="/file-view?id=${post.fileAttachment.id}" type="video/mp4">
						</video>
					</#if>
					<#if post.fileAttachment.fileType=="OTHER">
						<a href="/file-view?id=${post.fileAttachment.id}">Download File</a>
					</#if>				
				
				</#if>									
			</div>
			</#list>	
			<#else>
				<strong style="color: white;">No Posts</strong>
			</#if>		
		</div>
	</div>
</div>
<div id="wrapper">
	
	<div id="welcome" class="container">
		<div class="title">
			<h2>Welcome to Filtered Wall</h2>
		</div>
		<p>This is <strong>FilteredWall</strong>. The content filtering expert for social networking</p>
	</div>
</div>
<div id="copyright" class="container">
	
</div>
</body>
</html>
