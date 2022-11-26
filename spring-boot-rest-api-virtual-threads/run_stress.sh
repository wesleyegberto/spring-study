#!/usr/bin/env bash
set -e errexit

jmeter -n -t Jmeter-Stress-Test.jmx -l jmeter-results.csv -e -o stress_result
