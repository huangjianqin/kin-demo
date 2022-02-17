package org.kin.demo.maven;

/*
 * Copyright 2001-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ScanResult;
import org.apache.maven.model.Build;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author huangjianqin
 * @date 2022/2/16
 */
//定义Mojo, 其name本质上就是goal, 可定义执行lifecycle, 否则需要使用时定义
@Mojo(name = "printCodecs", defaultPhase = LifecyclePhase.COMPILE)
public class PrintCodecsMojo extends AbstractMojo {
    /**
     * maven project实例
     */
    @Parameter(defaultValue = "${project}")
    private MavenProject project;
    /** 自定义参数, 支持集合类 */
    @Parameter(defaultValue = "codecs")
    private String codecsFileName;

    @Override
    public void execute() {
        Build build = project.getBuild();
        //build目录
        String targetDir = build.getDirectory();
        //build output目录
        String classesDir = build.getOutputDirectory();
        //build 源码目录
        String sourceDir = build.getSourceDirectory();
        try {
            //编译目录
            List<String> compileClasspathElements = project.getCompileClasspathElements();
            List<String> runtimeClasspathElements = project.getRuntimeClasspathElements();
            //test编译目录
            List<String> testClasspathElements = project.getTestClasspathElements();

            //getLog()允许在控制台打印log
            getLog().info("target directory: " + targetDir);
            getLog().info("classes directory: " + classesDir);
            getLog().info("source directory: " + sourceDir);
            getLog().info("compileClasspathElements: " + compileClasspathElements);
            getLog().info("runtimeClasspathElements: " + runtimeClasspathElements);
            getLog().info("testClasspathElements: " + testClasspathElements);

            File codecsFile = new File(targetDir.concat("/").concat(codecsFileName));
            if (!codecsFile.exists()) {
                codecsFile.createNewFile();
            }

            //获取class输出path
            Collection<URL> javaFileUrls = new ArrayList<>(2);
            for (String runtimeClasspathElement : runtimeClasspathElements) {
                javaFileUrls.add(new File(runtimeClasspathElement).toURI().toURL());
            }

            //自定义classloader 加载这些类型
            URLClassLoader classLoader = new URLClassLoader(javaFileUrls.toArray(new URL[]{}),
                    Thread.currentThread().getContextClassLoader());
            classLoader.loadClass("org.kin.demo.maven.test.Main");
            Class.forName("org.kin.demo.maven.test.Main", true, classLoader);

            //使用ClassGraph并自定义classloader来加载类, 并把带@Codec的类名输出到指定文件中
            //之所以要手动加载class, 因为maven 执行goal时, 并不会加载类而且其working directory也不是项目目录
            try (PrintWriter pw = new PrintWriter(codecsFile)) {
                ClassGraph classGraph = new ClassGraph();
                classGraph.enableAnnotationInfo();
                try (ScanResult scanResult = classGraph
                        .enableClassInfo()
                        .disableJarScanning()
                        .addClassLoader(classLoader)
                        .scan()) {
                    for (ClassInfo classInfo : scanResult.getAllClasses()) {
                        if (!classInfo.isStandardClass() ||
                                classInfo.isAbstract() ||
                                !classInfo.isPublic()) {
                            //只打印类
                            getLog().info(String.format("ignore class '%s'", classInfo.getName()));
                            continue;
                        }

                        if (!classInfo.hasAnnotation(Codec.class.getName())) {
                            getLog().info(String.format("ignore class '%s'", classInfo.getName()));
                            continue;
                        }

                        pw.println(classInfo.getName());
                    }
                }
            }
        } catch (Exception e) {
            getLog().error(e);
        }
    }
}
