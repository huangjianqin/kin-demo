package org.kin.demo.maven;

import io.takari.maven.testing.TestResources;
import io.takari.maven.testing.executor.MavenRuntime;
import io.takari.maven.testing.executor.MavenVersions;
import io.takari.maven.testing.executor.junit.MavenJUnitTestRunner;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;

/**
 * mavne plugin 单元测试
 *
 * @author huangjianqin
 * @date 2022/2/16
 */
//测试基类
@RunWith(MavenJUnitTestRunner.class)
//maven版本
@MavenVersions({"3.6.3"})
public class PrintCodecsMojoTest {
    /**
     * maven pom.xml路径
     */
    @Rule
    public final TestResources resources = new TestResources();
    /** maven运行环境 */
    public final MavenRuntime maven;

    public PrintCodecsMojoTest(MavenRuntime.MavenRuntimeBuilder mavenBuilder) throws Exception {
        this.maven = mavenBuilder.withCliOptions("-B", "-U").build();
    }

    @Test
    public void test() throws Exception {
        //默认projects/{参数1}
        File basedir = resources.getBasedir("demo");
        maven.forProject(basedir)
                .execute("compile")
                .assertErrorFreeLog()
                .assertLogText("some build message");
    }
}
