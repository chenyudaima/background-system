package com.chenyudaima.util.opc;

import com.chenyudaima.constant.Property;
import com.chenyudaima.model.OpcUaConfig;
import org.eclipse.milo.opcua.sdk.client.OpcUaClient;
import org.eclipse.milo.opcua.sdk.client.api.identity.AnonymousProvider;
import org.eclipse.milo.opcua.sdk.client.api.identity.UsernameProvider;
import org.eclipse.milo.opcua.stack.core.security.SecurityPolicy;
import org.eclipse.milo.opcua.stack.core.types.builtin.LocalizedText;
import org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.UInteger;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * OpcUa操作
 * @author 沉鱼代码
 * @date 2023/2/10
 */
public class OpcUaUtil {
    /**
     * 创建OpcUa连接
     * @return OpcUa连接对象
     */
    public static OpcUaClient createOpcUaClient(OpcUaConfig opcUaConfig) throws Exception {
        Path securityTempDir = Paths.get(System.getProperty(Property.JAVA_IO_TMPDIR), "security");
        Files.createDirectories(securityTempDir);
        if (!Files.exists(securityTempDir)) {
            throw new Exception("无法创建安全目录: " + securityTempDir);
        }

        return OpcUaClient.create(opcUaConfig.getOpcUaUrl(),
                endpoints ->
                        endpoints.stream()
                                .filter(e -> e.getSecurityPolicyUri().equals(SecurityPolicy.None.getUri()))
                                .findFirst(),
                configBuilder ->
                        configBuilder
                                .setApplicationName(LocalizedText.english("eclipse milo opc-ua client"))
                                .setApplicationUri("urn:eclipse:milo:examples:client")
                                /**
                                 * 访问方式
                                 * 1.匿名访问 new AnonymousProvider()
                                 * 2.new UsernameProvider(userName,password) 带账号密码访问
                                 * 3.new UsernameProvider(userName,password,证书) 带账号密码证书访问
                                 */
                                .setIdentityProvider(opcUaConfig.getUserName() == null ? new AnonymousProvider() : new UsernameProvider(opcUaConfig.getUserName(), opcUaConfig.getPassword()))

                                //请求超时
                                .setRequestTimeout(UInteger.valueOf(opcUaConfig.getRequestTimeout()))
                                .build()
        );
    }
}
