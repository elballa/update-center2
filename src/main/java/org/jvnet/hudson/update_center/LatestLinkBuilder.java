package org.jvnet.hudson.update_center;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * Generates latest/index.html and latest/.htaccess
 *
 * The former lists all the available symlinks, and the latter actually defines the redirects.
 *
 * @author Kohsuke Kawaguchi
 */
public class LatestLinkBuilder implements Closeable {
    private IndexHtmlBuilder index;
    private PrintWriter htaccess;
    private boolean skipHtaccess;

    public LatestLinkBuilder(File dir, boolean skipHtaccess) throws IOException {
        this.skipHtaccess = skipHtaccess;
        if (!this.skipHtaccess) {
            System.out.println(String.format("Writing plugin symlinks and redirects to dir: %s", dir));

            index = new IndexHtmlBuilder(dir, "Permalinks to latest files");
            htaccess = new PrintWriter(new OutputStreamWriter(new FileOutputStream(new File(dir, ".htaccess"), true), "UTF-8"));

            htaccess.println("# GENERATED. DO NOT MODIFY.");
            // Redirect directive doesn't let us write redirect rules relative to the directory .htaccess exists,
            // so we are back to mod_rewrite
            htaccess.println("RewriteEngine on");
        }
    }

    public void close() throws IOException {
        if (!this.skipHtaccess) {
            index.close();
            htaccess.close();
        }
    }

    public void add(String localPath, String target) throws IOException {
        if (!this.skipHtaccess) {
            htaccess.printf("RewriteRule ^%s$ %s [R=302,L]\n", localPath.replace(".", "\\."), target);
            index.add(localPath, localPath);
        }
    }
}
