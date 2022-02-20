<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	version="1.0">
	<xsl:template match="/sommaire">
		<html>
			<head>
				<title>
					<xsl:value-of select="titre" />
				</title>
			</head>
			<body bgcolor="#FFFFFF">
				<h1>
					<xsl:value-of select="titre" />
				</h1>
				<table width="75%" border="1">
					<xsl:apply-templates select="chapitre" />
				</table>
			</body>
		</html>
	</xsl:template>
	<xsl:template match="chapitre">
		<tr>
			<td>
				<xsl:value-of select="numero" />
			</td>
			<td>
				<a href="{numero}.html">
					<xsl:value-of select="titre" />
				</a>
			</td>
		</tr>
	</xsl:template>
</xsl:stylesheet>
