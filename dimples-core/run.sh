echo "================================== 删除已有镜像 =================================="
docker rmi dimples/dimples-shiro:dimples dimples/dimples-shiro
echo "================================== 开始构建项目 =================================="
echo "================================== Steep 1 编译打包 =================================="
sudo mvn clean install
echo "================================== Steep 2 构建镜像 =================================="
sudo docker build -t dimples/dimples-shiro:dimples .
sudo docker rm dimples/dimples-shiro
sudo docker images
echo "================================== 构建项目成功 =================================="
echo "================================== 开始启动项目 =================================="
sudo docker run -d -p 8001:8001 --name shiro dimples/dimples-shiro:dimples
sudo docker ps -a
echo "================================== 启动项目成功 =================================="