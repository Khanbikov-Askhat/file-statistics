#!/bin/bash

# Скрипт настройки окружения
# Этот скрипт устанавливает необходимые пакеты

# Обновляем пакеты
apt-get update

# Устанавливаем Java
apt-get install -y openjdk-11-jdk

# Устанавливаем утилиты
apt-get install -y git
apt-get install -y maven

# Настраиваем переменные окружения
export JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64
export PATH=$JAVA_HOME/bin:$PATH

echo "Setup completed successfully"