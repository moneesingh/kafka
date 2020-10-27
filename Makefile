CWD := $(PWD)
ROOTDIR := $(CWD)
VERSION := latest

build:
	mvn clean install

image:  build
	echo $(CWD) $(ROOTDIR)
	cd $(ROOTDIR)/kconsumer; docker build -t kconsumer:$(VERSION) -f $(ROOTDIR)/kconsumer/Dockerfile .
	cd $(ROOTDIR)/kproducer; docker build -t kproducer:$(VERSION) -f $(ROOTDIR)/kproducer/Dockerfile .

clean:
	mvn clean
	docker rmi kconsumer:$(VERSION) kproducer:$(VERSION)
