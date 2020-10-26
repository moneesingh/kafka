CWD := $(PWD)
ROOTDIR := $(CWD)
VERSION := latest

image:
	echo $(CWD) $(ROOTDIR)
	cd $(ROOTDIR)/kconsumer; docker build -t kconsumer:$(VERSION) -f $(ROOTDIR)/kconsumer/src/main/docker/Dockerfile .
	cd $(ROOTDIR)/kproducer; docker build -t kproducer:$(VERSION) -f $(ROOTDIR)/kproducer/src/main/docker/Dockerfile .
	cd $(CWD)

clean:
	docker rmi kconsumer:$(VERSION) kproducer:$(VERSION)
