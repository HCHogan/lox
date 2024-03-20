# 定义变量
SRC_DIR := app
BUILD_DIR := build
JAR_NAME := ./build/jlox.jar
CLASS_PATH := ..

# 查找所有的 Java 源文件
JAVA_FILES := $(wildcard $(SRC_DIR)/*.java)

# 将 Java 文件转换为 class 文件
CLASS_FILES := $(patsubst $(SRC_DIR)/%.java,$(BUILD_DIR)/%.class,$(JAVA_FILES))

# 默认目标：打包 JAR
.PHONY: all
all: $(JAR_NAME)

# 打包 JAR 文件
$(JAR_NAME): $(CLASS_FILES)
	jar cvf $(JAR_NAME) -C $(BUILD_DIR) .

# 编译 Java 文件为 class 文件
$(BUILD_DIR)/%.class: $(SRC_DIR)/%.java | $(BUILD_DIR)
	javac -classpath $(CLASS_PATH) -d $(BUILD_DIR) $<

# 创建构建目录
$(BUILD_DIR):
	mkdir -p $(BUILD_DIR)

# 清理构建文件
.PHONY: clean
clean:
	rm -rf $(BUILD_DIR) $(JAR_NAME)
