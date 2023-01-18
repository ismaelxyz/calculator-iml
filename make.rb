#!/usr/bin/env ruby

require 'fileutils'
require 'optparse'

JAVA_BIN = 'PATH="$HOME/Programming/all/jdk-19.0.1/bin:PATH"'

def clear()
	Dir.glob("**/*.class") { |file|  FileUtils.rm_f(file) }
	FileUtils.rm_f('CalculatorIML.jar')
end

def build()
	system("#{JAVA_BIN} javac com/uptbal/calculator/view/Window.java")
end

def build_jar()
	jar_items = Dir.glob("**/*.class").join(" ").gsub("$", "\\$") + " assets"
	system("#{JAVA_BIN} jar --create --file CalculatorIML.jar --manifest=META-INF/MANIFEST.MF #{jar_items}")
end

def run()
	system("#{JAVA_BIN} java -jar CalculatorIML.jar")
end

OptionParser.new do |parser|
	parser.banner = "Usage: make.rb [options]"
	parser.on('-c', '--clear', 'Remove binaries') { |_| clear() }
	parser.on('-b', '--build', 'Build just classes') { |_| build() }

	parser.on('-j', '--build-jar', 'Build just jar file') { |_| build_jar() }
	parser.on('-f', '--build-full', 'Build project') do |_|
		clear()
		build()
		build_jar()
	end

	parser.on('-r', '--run', 'Run just') { |_| run() }
	parser.on('-x', '--run-full','Run project') do |_|
		clear()
		build()
		build_jar()
		run()
	end

end.parse!

