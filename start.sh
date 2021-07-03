#!/bin/bash

if [[ "${BASH_VERSINFO[0]}" -lt "4" ]]; then
        echo "!! Your system Bash is out of date: $BASH_VERSION"
        echo "!! Please upgrade to Bash 4 or greater."
        exit 2
fi

readonly Builder=/bin/vscode-builder
readonly Build="-build"
readonly Seed="-seed"
readonly Runserver='-runserver'
readonly Migrate='-migrate'

prgs-start() {
  $Builder $Build $Seed $Migrate $Runserver
}

prgs-build() {
  $Builder $Build
}

prgs-migrate() {
  $Builder $Migrate
}

prgs-seed() {
  $Builder $Seed
}

prgs-runserver() {
  $Builder $Runserver
}

cmd-ns() {
  local cmd="$1"; shift || true

  echo "No such command: $cmd"
}

main() {
  set -eo pipefail; [[ "$TRACE" ]] && set -x

  case "$0" in
      /start) prgs-start ;;
      /build) prgs-build ;;
      /seed) prgs-seed ;;
      /migrate) prgs-migrate ;;
      /runserver) prgs-runserver ;;
      *)                        cmd-ns "" "$@";
  esac
}

main