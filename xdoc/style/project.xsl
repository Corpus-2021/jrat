<?xml version="1.0"?>

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

	<xsl:output method="html"		indent="yes"/>

	<xsl:variable  name="base"		select="'style'" />
	<xsl:variable  name="bg_color"		select="'#006666'" />

	<xsl:param name="p_title"/>
	<xsl:param name="p_home"/>
	<xsl:param name="p_version"/>
	<xsl:param name="p_group_id"/>
	<xsl:param name="p_date"/>

	<xsl:template match="document">


		<html>
			<title><xsl:value-of select="@title"/></title>
			<link rel="stylesheet" href="{$base}/shiftone.css" type="text/css" />
		<head>
		</head>
			<body bgcolor="{$bg_color}">
				<table width="645" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td align="right" valign="top" colspan="3" width="645" height="86" background="{$base}/images/so_0.gif">

						<a class="jump" href="index.html">Home</a> |
						<a class="jump" href="lesser.html">License</a> |
						<a class="jump" href="api/index.html">API Docs</a>
						<br/>
						<a class="jump" href="{$p_home}">Summary</a> |
						<a class="jump" href="http://sourceforge.net/project/showfiles.php?group_id={$p_group_id}">Download</a>
						<br/>						
						<a class="jump" href="http://www.shiftone.org">ShiftOne</a>





						</td>
					</tr>

					<tr>
						<td width="40" background="{$base}/images/so_2L.gif"></td>
						<td width="565" bgcolor="#ffffff">

							<h1><xsl:value-of select="@title"/></h1>
							<xsl:apply-templates/>

						</td>
						<td width="40" background="{$base}/images/so_2R.gif"></td>
					</tr>
					<tr>
						<td colspan="3"><img src="{$base}/images/so_3.gif"/></td>
					</tr>
					<tr>
					<td colspan="2" align="right">

						<a class="jump" href="{$p_home}">
							<xsl:value-of select="$p_title"/>
						</a>
						<br/><br/>
						<a href="http://sourceforge.net">
							<img src="http://sourceforge.net/sflogo.php?group_id={$p_group_id}&amp;type=3" width="125" height="37" border="0" alt="SourceForge.net Logo"/>
						</a>
						<br/>
						<font size="-2">
						page regenerated <xsl:value-of select="$p_date"/>
						<br/>
						&#169; Jeff Drost
						</font>
					</td>
					</tr>
				</table>
				
			</body>
		</html>
	</xsl:template>

	<xsl:template match="intro">
		<a name="{@title}">
			<h1><xsl:value-of select="@title"/></h1>
			<xsl:apply-templates/>
		</a>
	</xsl:template>

	<xsl:template match="section-group">
		<xsl:for-each select="section">
			<a href="#{@title}"><xsl:value-of select="@title"/></a><br/>
		</xsl:for-each>
		<br/>
		<p><xsl:apply-templates/></p>
	</xsl:template>

	<xsl:template match="section">
		<a name="{@title}">
			<h2><xsl:value-of select="@title"/></h2>
			<p><xsl:apply-templates/></p>
		</a>
	</xsl:template>

	<xsl:template match="ol">
		<ol>
		<xsl:apply-templates select="li"/>
		</ol>
	</xsl:template>

	<xsl:template match="li">
		<li><xsl:apply-templates/></li>
	</xsl:template>

	<xsl:template match="quote">
		<i><xsl:apply-templates/></i>
	</xsl:template>

	<xsl:template match="bold">
		<b><xsl:apply-templates/></b>
	</xsl:template>

	<xsl:template match="p">
		<p><xsl:apply-templates/></p>
	</xsl:template>

	<xsl:template match="br">
		<br/>
	</xsl:template>

	<xsl:template match="code">
		<table align="center" width="90%" border="1" cellpadding="10" cellspacing="0">
			<tr>
				<td bgcolor="#dddddd">
					<pre><xsl:apply-templates select="text()"/></pre>
				</td>
			</tr>
		</table>
		<br/>
	</xsl:template>

	<xsl:template match="indent">
		<table border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td with="20"><img src="{$base}/image/clear.gif" width="20" height="1"/></td>
				<td><xsl:apply-templates/></td>
			</tr>
		</table>
	</xsl:template>

	<xsl:template match="h1">
		<h1><xsl:apply-templates/></h1>
	</xsl:template>

	<xsl:template match="h2">
		<h2><xsl:apply-templates/></h2>
	</xsl:template>

	<xsl:template match="h3">
		<h3><xsl:apply-templates/></h3>
	</xsl:template>

	<xsl:template match="h4">
		<h4><xsl:apply-templates/></h4>
	</xsl:template>

	<xsl:template match="a">
		<a href="{@href}"><xsl:apply-templates/></a>
	</xsl:template>

	<xsl:template match="figure">
		<br/>
		<center><img src="{@src}"/></center>
		<br/>
	</xsl:template>

</xsl:stylesheet>