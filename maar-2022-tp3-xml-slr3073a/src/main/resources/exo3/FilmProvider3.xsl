<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:foo="http://www.example.com/films/v3"
                exclude-result-prefixes="foo">
    <xsl:output method="html" encoding="UTF-8" indent="yes"/>

    <xsl:template match="/">
        <xsl:text disable-output-escaping='yes'>&lt;!DOCTYPE html&gt;&#xa;</xsl:text>
        <html lang="fr">
            <head>
                <title>Films</title>
            </head>

            <body>
                <h1>Films</h1>
                <table>
                    <thead>
                        <tr>Titre</tr>
                        <tr>
                            <xsl:text disable-output-escaping='yes'>Résumé</xsl:text>
                        </tr>
                    </thead>
                    <tbody>
                        <xsl:for-each select="/foo:films/foo:film">
                            <tr>
                                <td>
                                    <xsl:value-of select="foo:titre" disable-output-escaping="yes" />
                                </td>
                                <td>
                                    <xsl:value-of select="foo:resume/foo:text" disable-output-escaping="yes"/>
                                </td>
                            </tr>
                        </xsl:for-each>
                    </tbody>
                </table>
            </body>
        </html>
    </xsl:template>

</xsl:stylesheet>