#!/usr/bin/env bash
# ---------------------------------------------------------------------------
#  Arranca el microservicio contra el esquema FORO de Oracle Cloud.
#
#  Uso:  ./scripts/run.sh
#
#  Las credenciales y el Wallet los prepara entorno-oracle.sh desde el Llavero
#  de macOS; nunca se escriben en el repositorio.
# ---------------------------------------------------------------------------
set -euo pipefail
DIR="$(cd "$(dirname "$0")" && pwd)"
source "$DIR/entorno-oracle.sh"

echo "Arrancando contra ${ORACLE_USER}@${TNS_ALIAS:-fullstack3_tp} con el perfil oracle"
cd "$DIR/.."
exec ./mvnw -q spring-boot:run -Dspring-boot.run.profiles=oracle
