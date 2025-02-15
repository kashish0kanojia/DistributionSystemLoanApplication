Distributed System Group Project
          GROUP 20

PREREQUISITE:
1) Kubernetes node up and running
2) Docker desktop is up and running

Following are the steps to run the code, in the home directory of the code run the following commands:
1) docker build -t loan_app:0.0.1 .
2) docker tag loan_app:0.0.1 <docker_repository>/loan_app:0.0.2
3) docker push <docker_repository>/loan_app:0.0.2
4) Update the image name in jtApplication.yaml
5) kubectl apply -f jtApplication.yaml
6) kubectl port-forward --address 0.0.0.0 -n default service/api-service 30000:8080
7) Go to web browser and use URL: http://localhost:30000/ (for user login)
8) Go to web browser and use URL: http://localhost:30000/admin/products (for admin login)
