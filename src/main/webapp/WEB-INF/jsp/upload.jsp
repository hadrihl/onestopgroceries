<!doctype html>
<html>
<head>
<title>Upload Profile Image</title>
</head>
<body>
<h2>Upload Profile Image</h2>
<form method="post" action="/image-upload" modelattribute="file">
	<input type="file" name="file" accept="image/png, image/jpeg" /><br><br>
	<button type="submit">Upload</button>
</form>
</body>
</html>