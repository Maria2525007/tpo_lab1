#!/usr/bin/env bash

set -e
cd "$(dirname "$0")/.."
RESULTS_DIR="target/allure-results"
REPORT_DIR="target/allure-report"
HISTORY_LIMIT=10

mvn -q test

if [ -d "$REPORT_DIR/history" ]; then
  mkdir -p "$RESULTS_DIR"
  cp -r "$REPORT_DIR/history" "$RESULTS_DIR/"
fi

echo "Генерация отчёта"
if command -v allure &>/dev/null; then
  export ALLURE_RESULTS_LIMIT=$HISTORY_LIMIT
  allure generate "$RESULTS_DIR" -o "$REPORT_DIR" --clean
  echo ""
  echo "Отчёт (тренд: последние $HISTORY_LIMIT сборок): file://$(pwd)/$REPORT_DIR/index.html"
  echo "Открыть: allure open $REPORT_DIR"
else
  mvn -q io.qameta.allure:allure-maven:2.17.0:report
  echo ""
  echo "Открыть: allure open $REPORT_DIR"
fi